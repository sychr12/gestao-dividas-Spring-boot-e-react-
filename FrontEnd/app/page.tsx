export default function Home() {
  return (
    <main className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900 text-white">
      {/* Header */}
      <header className="flex items-center justify-between px-10 py-6 border-b border-white/10">
        <h1 className="text-2xl font-bold tracking-tight">
          💰 Dividas Control
        </h1>

        <nav className="space-x-6 text-sm text-gray-300">
          <a href="#" className="hover:text-white transition">
            Dashboard
          </a>
          <a href="#" className="hover:text-white transition">
            Relatórios
          </a>
          <a href="#" className="hover:text-white transition">
            Perfil
          </a>
        </nav>
      </header>

      {/* Hero */}
      <section className="flex flex-col items-center justify-center text-center px-6 py-28">
        <h2 className="text-5xl font-extrabold leading-tight max-w-3xl">
          Controle total das suas finanças,
          <span className="text-emerald-400"> sem dor de cabeça</span>
        </h2>

        <p className="mt-6 max-w-xl text-gray-300 text-lg">
          Gerencie dívidas, acompanhe pagamentos e visualize relatórios
          claros para tomar decisões inteligentes.
        </p>

        <div className="mt-10 flex gap-4">
          <button className="px-8 py-3 rounded-xl bg-emerald-500 text-black font-semibold hover:bg-emerald-400 transition">
            Começar agora
          </button>

          <button className="px-8 py-3 rounded-xl border border-white/20 hover:bg-white/10 transition">
            Ver relatórios
          </button>
        </div>
      </section>

      {/* Cards */}
      <section className="grid grid-cols-1 md:grid-cols-3 gap-6 px-10 pb-24">
        <Card
          title="📊 Relatórios inteligentes"
          text="Visualize suas dívidas, juros e pagamentos com gráficos claros."
        />
        <Card
          title="🔒 Segurança"
          text="Seus dados protegidos com autenticação e criptografia."
        />
        <Card
          title="⚡ Rápido e simples"
          text="Interface moderna, leve e pensada para produtividade."
        />
      </section>

      {/* Footer */}
      <footer className="text-center py-6 text-sm text-gray-400 border-t border-white/10">
        © {new Date().getFullYear()} Dividas Control — Todos os direitos reservados
      </footer>
    </main>
  );
}

function Card({ title, text }: { title: string; text: string }) {
  return (
    <div className="rounded-2xl bg-white/5 border border-white/10 p-6 hover:bg-white/10 transition">
      <h3 className="text-xl font-semibold mb-3">{title}</h3>
      <p className="text-gray-300 text-sm">{text}</p>
    </div>
  );
}
