"use client"

import {
  PieChart,
  Pie,
  Tooltip,
  ResponsiveContainer,
  Cell
} from "recharts"

const COLORS = ["#6366f1", "#22c55e", "#f97316", "#ef4444"]

type ChartProps = {
  data: {
    name: string
    value: number
  }[]
}

export default function DebtChart({ data }: ChartProps) {
  return (
    <div className="bg-white rounded-2xl shadow-md p-6 h-80">
      <h2 className="text-lg font-semibold mb-4">
        Dívidas por cartão
      </h2>

      <ResponsiveContainer width="100%" height="100%">
        <PieChart>
          <Pie
            data={data}
            dataKey="value"
            nameKey="name"
            outerRadius={100}
            label
          >
            {data.map((_, index) => (
              <Cell
                key={index}
                fill={COLORS[index % COLORS.length]}
              />
            ))}
          </Pie>
          <Tooltip />
        </PieChart>
      </ResponsiveContainer>
    </div>
  )
}
