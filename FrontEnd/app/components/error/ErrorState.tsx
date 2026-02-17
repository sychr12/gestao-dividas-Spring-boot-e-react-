"use client"

import { AlertTriangle, RefreshCw, Home } from "lucide-react"

type ErrorStateProps = {
  error: string
  onRetry: () => void
  onGoHome?: () => void
}

export default function ErrorState({ error, onRetry, onGoHome }: ErrorStateProps) {
  return (
    <div className="flex min-h-screen items-center justify-center bg-linear-to-br from-slate-50 to-slate-100 p-4">
      <div className="w-full max-w-md">
        <div className="bg-white rounded-2xl shadow-xl p-8 text-center space-y-6">
          
          {/* Icon */}
          <div className="flex justify-center">
            <div className="relative">
              <div className="absolute inset-0 bg-red-100 rounded-full animate-ping opacity-75" />
              <div className="relative bg-red-100 text-red-600 p-4 rounded-full">
                <AlertTriangle size={40} />
              </div>
            </div>
          </div>

          {/* Title */}
          <div>
            <h2 className="text-2xl font-bold text-slate-900 mb-2">
              Ops! Algo deu errado
            </h2>
            <p className="text-slate-600">
              {error}
            </p>
          </div>

          {/* Details */}
          <div className="bg-slate-50 border border-slate-200 rounded-lg p-4 text-left">
            <p className="text-sm text-slate-700 mb-2">
              <strong>Possíveis causas:</strong>
            </p>
            <ul className="text-sm text-slate-600 space-y-1 list-disc list-inside">
              <li>Backend não está rodando</li>
              <li>Verifique se está em <code className="bg-slate-200 px-1 rounded">http://localhost:8080</code></li>
              <li>Problema de conexão com o banco de dados</li>
              <li>Configuração de CORS</li>
            </ul>
          </div>

          {/* Actions */}
          <div className="flex flex-col sm:flex-row gap-3 pt-2">
            <button
              onClick={onRetry}
              className="flex-1 flex items-center justify-center gap-2 px-6 py-3 bg-slate-900 text-white rounded-lg hover:bg-slate-800 transition-colors font-medium"
            >
              <RefreshCw size={18} />
              Tentar novamente
            </button>
            
            {onGoHome && (
              <button
                onClick={onGoHome}
                className="flex-1 flex items-center justify-center gap-2 px-6 py-3 border-2 border-slate-200 text-slate-700 rounded-lg hover:bg-slate-50 transition-colors font-medium"
              >
                <Home size={18} />
                Ir para início
              </button>
            )}
          </div>

          {/* Help Text */}
          <p className="text-xs text-slate-500 pt-4">
            Se o problema persistir, verifique os logs do backend ou entre em contato com o suporte.
          </p>

        </div>
      </div>
    </div>
  )
}