"use client"

import { FileQuestion, PlusCircle } from "lucide-react"

type EmptyStateProps = {
  title?: string
  description?: string
  actionLabel?: string
  onAction?: () => void
}

export default function EmptyState({
  title = "Nenhum dado disponível",
  description = "Não encontramos nenhuma informação para exibir no momento.",
  actionLabel = "Adicionar dados",
  onAction
}: EmptyStateProps) {
  return (
    <div className="flex min-h-screen items-center justify-center bg-linear-to-br from-slate-50 to-slate-100 p-4">
      <div className="w-full max-w-md">
        <div className="bg-white rounded-2xl shadow-xl p-8 text-center space-y-6">
          
          {/* Icon */}
          <div className="flex justify-center">
            <div className="bg-slate-100 text-slate-400 p-6 rounded-full">
              <FileQuestion size={48} strokeWidth={1.5} />
            </div>
          </div>

          {/* Content */}
          <div>
            <h2 className="text-2xl font-bold text-slate-900 mb-2">
              {title}
            </h2>
            <p className="text-slate-600">
              {description}
            </p>
          </div>

          {/* Action */}
          {onAction && (
            <button
              onClick={onAction}
              className="flex items-center justify-center gap-2 w-full px-6 py-3 bg-slate-900 text-white rounded-lg hover:bg-slate-800 transition-colors font-medium"
            >
              <PlusCircle size={18} />
              {actionLabel}
            </button>
          )}

        </div>
      </div>
    </div>
  )
}