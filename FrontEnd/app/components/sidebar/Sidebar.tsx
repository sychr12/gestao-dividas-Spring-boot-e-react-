"use client"

import Link from "next/link"
import { usePathname } from "next/navigation"

export default function Sidebar() {
  const pathname = usePathname()

  const menu = [
    { label: "Dashboard", href: "/home", icon: "📊" },
    { label: "Perfil", href: "/perfil", icon: "👤" },
    { label: "Relatórios", href: "/historicodoscartoes", icon: "📄" },
    { label: "Cartão mais endividado", href: "/topcartaomaisdividado", icon: "💳" },
  ]

  return (
    <aside className="w-64 min-h-screen bg-slate-900 text-slate-100 flex flex-col justify-between">
      
      {/* TOPO */}
      <div className="p-6">
        <h1 className="text-xl font-bold mb-10 flex items-center gap-2">
          💰 <span>Dívidas</span>
        </h1>

        <nav className="flex flex-col gap-2 text-sm">
          {menu.map(item => {
            const isActive = pathname === item.href

            return (
              <Link
                key={item.href}
                href={item.href}
                className={`flex items-center gap-3 px-4 py-3 rounded-lg transition
                  ${
                    isActive
                      ? "bg-slate-800 text-white"
                      : "text-slate-400 hover:bg-slate-800 hover:text-white"
                  }
                `}
              >
                <span className="text-base">{item.icon}</span>
                <span>{item.label}</span>
              </Link>
            )
          })}
        </nav>
      </div>

      {/* RODAPÉ */}
      <div className="p-6 border-t border-slate-800">
        <div className="flex items-center gap-3 mb-4">
          <div className="w-10 h-10 rounded-full bg-slate-700 flex items-center justify-center font-semibold">
            L
          </div>

          <div>
            <p className="text-sm font-medium">Luiz</p>
            <p className="text-xs text-slate-400">Conta ativa</p>
          </div>
        </div>

        <button className="w-full text-left text-sm text-slate-400 hover:text-white hover:bg-slate-800 px-4 py-2 rounded-lg transition">
          🚪 Sair
        </button>
      </div>
    </aside>
  )
}
