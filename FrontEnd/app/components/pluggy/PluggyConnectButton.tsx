import type { Metadata } from "next"
import { Inter } from "next/font/google"
import "./globals.css"

const inter = Inter({
  subsets: ["latin"],
  variable: "--font-inter",
})

export const metadata: Metadata = {
  title: "Gerenciamento de Dívidas",
  description: "Aplicação de controle financeiro com Pluggy",
  keywords: ["finanças", "dívidas", "cartões", "controle financeiro"],
  authors: [{ name: "Sistema de Finanças" }],
  viewport: "width=device-width, initial-scale=1",
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="pt-BR">
      <head>
        {/* Pluggy Connect Widget - Script correto */}
        <script
          src="https://api.pluggy.ai/accounts"
          async
        />
      </head>
      <body className={`${inter.variable} antialiased`}>
        {children}
      </body>
    </html>
  )
}