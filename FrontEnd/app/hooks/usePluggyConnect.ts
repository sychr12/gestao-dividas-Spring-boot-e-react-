"use client"

import { useEffect, useRef, useState, useCallback } from "react"

declare global {
  interface Window {
    PluggyConnect: any
  }
}

type UsePluggyConnectOptions = {
  onSuccess?: (itemData: any) => void
  onError?: (error: any) => void
  onClose?: () => void
  onOpen?: () => void
  includeSandbox?: boolean
}

export function usePluggyConnect(
  connectToken: string | null,
  options: UsePluggyConnectOptions = {}
) {
  const {
    onSuccess,
    onError,
    onClose,
    onOpen,
    includeSandbox = process.env.NODE_ENV === 'development',
  } = options

  const pluggyRef = useRef<any>(null)
  const [isReady, setIsReady] = useState(false)
  const [isOpen, setIsOpen] = useState(false)

  useEffect(() => {
    const initPluggy = () => {
      if (window.PluggyConnect && connectToken) {
        try {
          pluggyRef.current = new window.PluggyConnect({
            connectToken,
            includeSandbox,
            onSuccess: (itemData: any) => {
              console.log('✅ Pluggy Success:', itemData)
              setIsOpen(false)
              onSuccess?.(itemData)
            },
            onError: (error: any) => {
              console.error('❌ Pluggy Error:', error)
              setIsOpen(false)
              onError?.(error)
            },
            onClose: () => {
              console.log('🚪 Pluggy Closed')
              setIsOpen(false)
              onClose?.()
            },
            onOpen: () => {
              console.log('🔓 Pluggy Opened')
              setIsOpen(true)
              onOpen?.()
            },
          })
          setIsReady(true)
        } catch (error) {
          console.error('Erro ao inicializar Pluggy:', error)
          setIsReady(false)
        }
      }
    }

    if (window.PluggyConnect) {
      initPluggy()
    } else {
      const handleLoad = () => {
        setTimeout(initPluggy, 100)
      }
      window.addEventListener('load', handleLoad)
      
      // Também tentar após um pequeno delay
      const timeoutId = setTimeout(initPluggy, 1000)
      
      return () => {
        window.removeEventListener('load', handleLoad)
        clearTimeout(timeoutId)
      }
    }
  }, [connectToken, includeSandbox, onSuccess, onError, onClose, onOpen])

  const open = useCallback(() => {
    if (!isReady) {
      console.warn('Pluggy Connect ainda não está pronto')
      return
    }
    if (!pluggyRef.current) {
      console.error('Pluggy Connect não foi inicializado')
      return
    }
    try {
      pluggyRef.current.open()
    } catch (error) {
      console.error('Erro ao abrir Pluggy:', error)
    }
  }, [isReady])

  const close = useCallback(() => {
    if (pluggyRef.current) {
      try {
        pluggyRef.current.close()
      } catch (error) {
        console.error('Erro ao fechar Pluggy:', error)
      }
    }
  }, [])

  return {
    open,
    close,
    isReady,
    isOpen,
  }
}