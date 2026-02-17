"use client"

import { useEffect, useState } from "react"
import Card from "@/app/components/card/Card"
import Sidebar from "@/app/components/sidebar/Sidebar"
import DebtChart from "@/app/components/chart/DebtChart"
import DashboardSkeleton from "@/app/components/skeleton/DashboardSkeleton"
import ErrorState from "@/app/components/error/ErrorState"
import EmptyState from "@/app/components/empty/EmptyState"

type CardData = {
  name: string
  used: number
  limit: number
}

type ChartData = {
  name: string
  value: number
}

type DashboardResponse = {
  totalDebts: number
  cardsCount: number
  usedLimitPercent: number
  cards: CardData[]
  chartData: ChartData[]
}

const API_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080"

export default function Home() {
  const [data, setData] = useState<DashboardResponse | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  const fetchDashboard = async () => {
    const controller = new AbortController()
    setLoading(true)
    setError(null)

    try {
      const res = await fetch(`${API_URL}/dashboard`, { 
        signal: controller.signal,
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
      })
      
      if (!res.ok) {
        throw new Error(`Erro HTTP ${res.status}: ${res.statusText}`)
      }
      
      const json = await res.json()
      setData(json)
    } catch (err) {
      if (err instanceof Error && err.name !== 'AbortError') {
        console.error('Erro ao carregar dashboard:', err)
        setError('Não foi possível conectar ao servidor.')
      }
    } finally {
      setLoading(false)
    }

    return () => controller.abort()
  }

  useEffect(() => {
    fetchDashboard()
  }, [])

  // Loading State
  if (loading) {
    return <DashboardSkeleton />
  }

  // Error State
  if (error) {
    return (
      <ErrorState
        error={error}
        onRetry={fetchDashboard}
        onGoHome={() => window.location.reload()}
      />
    )
  }

  // Empty State
  if (!data || data.cardsCount === 0) {
    return (
      <EmptyState
        title="Nenhum cartão cadastrado"
        description="Você ainda não possui cartões cadastrados. Adicione seu primeiro cartão para começar a acompanhar suas finanças."
        actionLabel="Adicionar cartão"
        onAction={() => {
          // Navegar para página de adicionar cartão
          // router.push('/cards/add')
          alert('Funcionalidade em desenvolvimento')
        }}
      />
    )
  }

  const highRiskCards = data.cards.filter(
    card => (card.used / card.limit) * 100 > 80
  )

  return (
    <div className="flex min-h-screen bg-slate-100">
      <Sidebar />

      <main className="flex-1 p-6 lg:p-8">
        <div className="max-w-7xl mx-auto space-y-10">

          {/* HEADER */}
          <header className="animate-fade-in">
            <h1 className="text-2xl font-semibold text-slate-900">
              Dashboard
            </h1>
            <p className="text-sm text-slate-500 mt-1">
              Visão geral das suas finanças
            </p>
          </header>

          {/* KPIs */}
          <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 animate-slide-up">
            <Card
              title="Total em dívidas"
              value={`R$ ${data.totalDebts.toLocaleString("pt-BR")}`}
            />
            <Card
              title="Cartões ativos"
              value={data.cardsCount.toString()}
            />
            <Card
              title="Uso total do limite"
              value={`${data.usedLimitPercent}%`}
            />
          </section>

          {/* GRÁFICO + ALERTAS */}
          <section className="grid grid-cols-1 lg:grid-cols-3 gap-6 animate-slide-up" style={{ animationDelay: '100ms' }}>

            <div className="lg:col-span-2 bg-white rounded-2xl shadow-md p-6 hover:shadow-lg transition-shadow">
              <h2 className="text-lg font-semibold text-slate-900 mb-4">
                Dívidas por bandeira
              </h2>
              <DebtChart data={data.chartData} />
            </div>

            <div className="bg-white rounded-2xl shadow-md p-6 space-y-4 hover:shadow-lg transition-shadow">
              <h2 className="text-lg font-semibold text-slate-900">
                Alertas
              </h2>

              {highRiskCards.length === 0 && (
                <div className="flex items-center gap-2 text-sm text-green-600 bg-green-50 border border-green-200 rounded-lg p-4">
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                  </svg>
                  <span>Nenhum cartão em risco no momento</span>
                </div>
              )}

              {highRiskCards.map((card, index) => {
                const percent = Math.round(
                  (card.used / card.limit) * 100
                )

                return (
                  <div
                    key={card.name}
                    className="bg-red-50 border border-red-200 rounded-lg p-4 animate-fade-in"
                    style={{ animationDelay: `${index * 50}ms` }}
                  >
                    <div className="flex items-start justify-between">
                      <div>
                        <p className="text-sm font-semibold text-red-700">
                          {card.name}
                        </p>
                        <p className="text-xs text-red-600 mt-1">
                          {percent}% do limite utilizado
                        </p>
                      </div>
                      <svg className="w-5 h-5 text-red-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                      </svg>
                    </div>
                  </div>
                )
              })}
            </div>

          </section>

          {/* RANKING */}
          <section className="bg-white rounded-2xl shadow-md p-6 hover:shadow-lg transition-shadow animate-slide-up" style={{ animationDelay: '200ms' }}>
            <h2 className="text-lg font-semibold text-slate-900 mb-4">
              Cartões mais endividados
            </h2>

            <ul className="space-y-3">
              {[...data.cards]
                .sort(
                  (a, b) =>
                    b.used / b.limit - a.used / a.limit
                )
                .map((card, index) => {
                  const percent = Math.round(
                    (card.used / card.limit) * 100
                  )

                  return (
                    <li
                      key={card.name}
                      className="flex justify-between items-center bg-slate-50 rounded-lg px-4 py-3 hover:bg-slate-100 transition-colors cursor-pointer"
                    >
                      <div className="flex items-center gap-3">
                        <span className="flex items-center justify-center w-8 h-8 bg-slate-200 text-slate-700 rounded-full text-sm font-bold">
                          #{index + 1}
                        </span>
                        <span className="text-sm font-medium text-slate-900">
                          {card.name}
                        </span>
                      </div>

                      <div className="flex items-center gap-3">
                        <div className="w-24 h-2 bg-slate-200 rounded-full overflow-hidden">
                          <div
                            className={`h-full transition-all ${
                              percent > 80
                                ? "bg-red-500"
                                : percent > 60
                                ? "bg-yellow-500"
                                : "bg-green-500"
                            }`}
                            style={{ width: `${percent}%` }}
                          />
                        </div>
                        <span
                          className={`text-sm font-semibold min-w-12 text-right ${
                            percent > 80
                              ? "text-red-600"
                              : percent > 60
                              ? "text-yellow-600"
                              : "text-slate-700"
                          }`}
                        >
                          {percent}%
                        </span>
                      </div>
                    </li>
                  )
                })}
            </ul>
          </section>

        </div>
      </main>
    </div>
  )
}