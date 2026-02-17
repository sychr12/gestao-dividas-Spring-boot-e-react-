"use client"

export default function DashboardSkeleton() {
  return (
    <div className="flex min-h-screen bg-slate-100">
      {/* Sidebar Skeleton */}
      <div className="w-64 bg-white border-r border-slate-200 p-6">
        <div className="h-8 bg-slate-200 rounded animate-pulse mb-8" />
        <div className="space-y-4">
          {[1, 2, 3, 4].map((i) => (
            <div key={i} className="h-10 bg-slate-200 rounded animate-pulse" />
          ))}
        </div>
      </div>

      <main className="flex-1 p-6 lg:p-8">
        <div className="max-w-7xl mx-auto space-y-10">
          
          {/* Header Skeleton */}
          <header>
            <div className="h-8 w-48 bg-slate-200 rounded animate-pulse mb-2" />
            <div className="h-4 w-64 bg-slate-200 rounded animate-pulse" />
          </header>

          {/* KPIs Skeleton */}
          <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
            {[1, 2, 3].map((i) => (
              <div key={i} className="bg-white rounded-2xl shadow-md p-6">
                <div className="h-4 w-32 bg-slate-200 rounded animate-pulse mb-4" />
                <div className="h-8 w-24 bg-slate-200 rounded animate-pulse" />
              </div>
            ))}
          </section>

          {/* Chart + Alerts Skeleton */}
          <section className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            <div className="lg:col-span-2 bg-white rounded-2xl shadow-md p-6">
              <div className="h-6 w-48 bg-slate-200 rounded animate-pulse mb-4" />
              <div className="h-64 bg-slate-200 rounded-lg animate-pulse" />
            </div>

            <div className="bg-white rounded-2xl shadow-md p-6">
              <div className="h-6 w-32 bg-slate-200 rounded animate-pulse mb-4" />
              <div className="space-y-3">
                {[1, 2].map((i) => (
                  <div key={i} className="h-20 bg-slate-200 rounded-lg animate-pulse" />
                ))}
              </div>
            </div>
          </section>

          {/* Ranking Skeleton */}
          <section className="bg-white rounded-2xl shadow-md p-6">
            <div className="h-6 w-56 bg-slate-200 rounded animate-pulse mb-4" />
            <div className="space-y-3">
              {[1, 2, 3, 4, 5].map((i) => (
                <div key={i} className="h-14 bg-slate-50 rounded-lg animate-pulse" />
              ))}
            </div>
          </section>

        </div>
      </main>
    </div>
  )
}