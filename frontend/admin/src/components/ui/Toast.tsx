import { X, CheckCircle, XCircle, AlertCircle, Info } from "lucide-react"
import { useEffect } from "react"

export type ToastType = "success" | "error" | "warning" | "info"

interface ToastProps {
  message: string
  type?: ToastType
  duration?: number
  onClose: () => void
  isVisible: boolean
}

const toastConfig = {
  success: {
    icon: CheckCircle,
    bgColor: "bg-green-500",
    textColor: "text-white",
    iconColor: "text-white"
  },
  error: {
    icon: XCircle,
    bgColor: "bg-red-500",
    textColor: "text-white",
    iconColor: "text-white"
  },
  warning: {
    icon: AlertCircle,
    bgColor: "bg-yellow-500",
    textColor: "text-white",
    iconColor: "text-white"
  },
  info: {
    icon: Info,
    bgColor: "bg-blue-500",
    textColor: "text-white",
    iconColor: "text-white"
  }
}

export function Toast({ 
  message, 
  type = "info", 
  duration = 3000, 
  onClose, 
  isVisible 
}: ToastProps) {
  const config = toastConfig[type]
  const Icon = config.icon

  useEffect(() => {
    if (isVisible && duration > 0) {
      const timer = setTimeout(() => {
        onClose()
      }, duration)

      return () => clearTimeout(timer)
    }
  }, [isVisible, duration, onClose])

  if (!isVisible) return null

  return (
    <div className="fixed top-4 right-4 z-[100] animate-in slide-in-from-top-2 duration-300">
      <div className={`${config.bgColor} ${config.textColor} rounded-lg shadow-lg p-4 pr-12 min-w-[300px] max-w-md relative`}>
        <div className="flex items-start gap-3">
          <Icon className={`h-5 w-5 ${config.iconColor} flex-shrink-0 mt-0.5`} />
          <p className="text-sm font-medium flex-1">{message}</p>
        </div>
        <button
          onClick={onClose}
          className="absolute top-4 right-4 text-white/80 hover:text-white transition-colors"
          aria-label="Close notification"
        >
          <X className="h-4 w-4" />
        </button>
      </div>
    </div>
  )
}
