
import { useState, useEffect } from "react"
import { useNavigate, useLocation } from "react-router-dom"
import LayoutDefault from "@/components/layout/LayoutDefault"
import { ROUTES } from "@/routes"

export type ViewType = "overview" | "movies" | "showtimes" | "theaters" | "tickets"

export function CinemaManagementDashboard() {
  const [currentView, setCurrentView] = useState<ViewType>("overview")
  const navigate = useNavigate()
  const location = useLocation()

  // Sync current view with URL
  useEffect(() => {
    const path = location.pathname
    switch (path) {
      case ROUTES.MOVIES:
        setCurrentView("movies")
        break
      case ROUTES.SHOWTIMES:
        setCurrentView("showtimes")
        break
      case ROUTES.THEATERS:
        setCurrentView("theaters")
        break
      case ROUTES.TICKETS:
        setCurrentView("tickets")
        break
      default:
        setCurrentView("overview")
    }
  }, [location.pathname])

  const handleViewChange = (view: ViewType) => {
    setCurrentView(view)
    
    // Navigate to corresponding route
    switch (view) {
      case "movies":
        navigate(ROUTES.MOVIES)
        break
      case "showtimes":
        navigate(ROUTES.SHOWTIMES)
        break
      case "theaters":
        navigate(ROUTES.THEATERS)
        break
      case "tickets":
        navigate(ROUTES.TICKETS)
        break
      default:
        navigate(ROUTES.DASHBOARD)
    }
  }

  const renderCurrentView = () => {
    switch (currentView) {
      case "overview":
        return (
          <div className="space-y-6">
            <div className="border-b border-border pb-4">
              <h1 className="text-2xl font-bold text-foreground">Tổng quan hệ thống</h1>
              <p className="text-muted-foreground">Thống kê tổng quan về hoạt động rạp phim</p>
            </div>
            
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
              <div className="bg-card rounded-lg border border-border p-6">
                <h3 className="text-sm font-medium text-muted-foreground">Tổng số phim</h3>
                <p className="text-2xl font-bold text-foreground">24</p>
              </div>
              <div className="bg-card rounded-lg border border-border p-6">
                <h3 className="text-sm font-medium text-muted-foreground">Phòng chiếu</h3>
                <p className="text-2xl font-bold text-foreground">8</p>
              </div>
              <div className="bg-card rounded-lg border border-border p-6">
                <h3 className="text-sm font-medium text-muted-foreground">Vé đã bán hôm nay</h3>
                <p className="text-2xl font-bold text-foreground">156</p>
              </div>
              <div className="bg-card rounded-lg border border-border p-6">
                <h3 className="text-sm font-medium text-muted-foreground">Doanh thu hôm nay</h3>
                <p className="text-2xl font-bold text-foreground">12.5M VNĐ</p>
              </div>
            </div>
          </div>
        )
      case "movies":
        return (
          <div className="space-y-6">
            <div className="border-b border-border pb-4">
              <h1 className="text-2xl font-bold text-foreground">Quản lý phim</h1>
              <p className="text-muted-foreground">Quản lý danh sách phim trong hệ thống</p>
            </div>
            <div className="bg-card rounded-lg border border-border p-6">
              <p className="text-muted-foreground">Danh sách phim sẽ được hiển thị ở đây</p>
            </div>
          </div>
        )
      case "showtimes":
        return (
          <div className="space-y-6">
            <div className="border-b border-border pb-4">
              <h1 className="text-2xl font-bold text-foreground">Lịch chiếu</h1>
              <p className="text-muted-foreground">Quản lý lịch chiếu phim</p>
            </div>
            <div className="bg-card rounded-lg border border-border p-6">
              <p className="text-muted-foreground">Lịch chiếu sẽ được hiển thị ở đây</p>
            </div>
          </div>
        )
      case "theaters":
        return (
          <div className="space-y-6">
            <div className="border-b border-border pb-4">
              <h1 className="text-2xl font-bold text-foreground">Phòng chiếu</h1>
              <p className="text-muted-foreground">Quản lý phòng chiếu và ghế ngồi</p>
            </div>
            <div className="bg-card rounded-lg border border-border p-6">
              <p className="text-muted-foreground">Danh sách phòng chiếu sẽ được hiển thị ở đây</p>
            </div>
          </div>
        )
      case "tickets":
        return (
          <div className="space-y-6">
            <div className="border-b border-border pb-4">
              <h1 className="text-2xl font-bold text-foreground">Vé đã bán</h1>
              <p className="text-muted-foreground">Thống kê và quản lý vé đã bán</p>
            </div>
            <div className="bg-card rounded-lg border border-border p-6">
              <p className="text-muted-foreground">Danh sách vé đã bán sẽ được hiển thị ở đây</p>
            </div>
          </div>
        )
      default:
        return null
    }
  }

  return (
    <LayoutDefault currentView={currentView} onViewChange={handleViewChange}>
      {renderCurrentView()}
    </LayoutDefault>
  )
}
