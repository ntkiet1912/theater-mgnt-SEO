import { useState, useCallback } from "react"
import type { ToastType } from "@/components/ui/Toast"

interface ToastState {
  isVisible: boolean
  message: string
  type: ToastType
}

export function useToast() {
  const [toast, setToast] = useState<ToastState>({
    isVisible: false,
    message: "",
    type: "info"
  })

  const showToast = useCallback((message: string, type: ToastType = "info") => {
    setToast({
      isVisible: true,
      message,
      type
    })
  }, [])

  const hideToast = useCallback(() => {
    setToast(prev => ({
      ...prev,
      isVisible: false
    }))
  }, [])

  const success = useCallback((message: string) => {
    showToast(message, "success")
  }, [showToast])

  const error = useCallback((message: string) => {
    showToast(message, "error")
  }, [showToast])

  const warning = useCallback((message: string) => {
    showToast(message, "warning")
  }, [showToast])

  const info = useCallback((message: string) => {
    showToast(message, "info")
  }, [showToast])

  return {
    toast,
    showToast,
    hideToast,
    success,
    error,
    warning,
    info
  }
}
