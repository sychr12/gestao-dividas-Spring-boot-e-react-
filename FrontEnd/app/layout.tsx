import type { Metadata } from "next"
import { Inter } from "next/font/google"
import Script from "next/script"
import "./globals.css"

const inter = Inter({
  subsets: ["latin"],
  variable: "--font-inter",
})

export const metadata: Metadata = {
  title: "Gerenciamento de Dívidas",
  description: "Aplicação de controle financeiro com Pluggy",
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="pt-BR">
      <body className={`${inter.variable} antialiased`}>
        {children}

        {/* Script oficial do Pluggy Connect */}
        <Script
          src="https://connect.pluggy.ai/connect.js"
          strategy="afterInteractive"
        />
      </body>
    </html>
  )
}
