type CardProps = {
  title: string
  value: string
}

export default function Card({ title, value }: CardProps) {
  return (
    <div className="bg-white rounded-2xl shadow-md p-6">
      <span className="text-sm text-gray-500">{title}</span>
      <strong className="text-2xl block mt-1">{value}</strong>
    </div>
  )
}
