"use client"

import { useState, useEffect, useCallback } from 'react'

type UseFetchOptions = {
  retries?: number
  retryDelay?: number
  timeout?: number
}

type UseFetchReturn<T> = {
  data: T | null
  loading: boolean
  error: string | null
  refetch: () => void
}

export function useFetch<T>(
  url: string,
  options: UseFetchOptions = {}
): UseFetchReturn<T> {
  const {
    retries = 3,
    retryDelay = 1000,
    timeout = 10000
  } = options

  const [data, setData] = useState<T | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const fetchData = useCallback(async () => {
    setLoading(true)
    setError(null)

    let lastError: Error | null = null

    for (let attempt = 0; attempt <= retries; attempt++) {
      try {
        const controller = new AbortController()
        const timeoutId = setTimeout(() => controller.abort(), timeout)

        const response = await fetch(url, {
          signal: controller.signal,
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
          },
        })

        clearTimeout(timeoutId)

        if (!response.ok) {
          throw new Error(`HTTP ${response.status}: ${response.statusText}`)
        }

        const json = await response.json()
        setData(json)
        setError(null)
        return
      } catch (err) {
        lastError = err as Error
        
        if (err instanceof Error && err.name === 'AbortError') {
          lastError = new Error('Tempo de resposta excedido')
        }

        if (attempt < retries) {
          await new Promise(resolve => setTimeout(resolve, retryDelay * (attempt + 1)))
        }
      }
    }

    setError(lastError?.message || 'Erro ao carregar dados')
    setData(null)
  }, [url, retries, retryDelay, timeout])

  useEffect(() => {
    fetchData()
  }, [fetchData])

  const refetch = useCallback(() => {
    fetchData()
  }, [fetchData])

  useEffect(() => {
    setLoading(false)
  }, [data, error])

  return { data, loading, error, refetch }
}