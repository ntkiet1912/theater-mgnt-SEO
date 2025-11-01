
import { Film, Calendar, Theater, Ticket, LayoutDashboard, Settings, Moon, Sun, LogOut } from "lucide-react"
import { useNavigate, NavLink } from "react-router-dom"
import { cn } from "@/utils/cn"
import { useTheme } from "@/providers/ThemeProvider"
import { ROUTES } from "@/routes"
import { logOut } from "@/services/authenticationService"

export function Sidebar() {
  const { theme, toggleTheme } = useTheme()
  const navigate = useNavigate()


  const handleLogout = () => {
    logOut();
    window.location.href = "/login";
  };

  const menuItems = [
    { id: "overview", label: "Dashboard", icon: LayoutDashboard, path: ROUTES.DASHBOARD },
    { id: "movies", label: "Manage Movies", icon: Film, path: ROUTES.MOVIES },
    { id: "showtimes", label: "Showtimes", icon: Calendar, path: ROUTES.SHOWTIMES },
    { id: "theaters", label: "Theaters", icon: Theater, path: ROUTES.THEATERS },
    { id: "tickets", label: "Sold Tickets", icon: Ticket, path: ROUTES.TICKETS },
  ]

  return (
    <aside className="w-64 border-r border-sidebar-border bg-sidebar">
      <div className="flex h-16 items-center gap-2 border-b border-sidebar-border px-6">
        <Film className="h-6 w-6 text-sidebar-primary" />
        <span className="text-lg font-semibold text-sidebar-foreground">Cinema Manager</span>
      </div>

      <nav className="space-y-1 p-4">
        <div className="mb-4">
          <p className="mb-2 px-3 text-xs font-medium uppercase tracking-wider text-muted-foreground">Quản lý</p>
          {menuItems.map((item) => {
            const Icon = item.icon
            return (
              <NavLink
                key={item.id}
                to={item.path}
                className={({ isActive }) =>
                  cn(
                    "flex w-full items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium transition-colors",
                    isActive
                      ? "bg-sidebar-accent text-sidebar-accent-foreground"
                      : "text-sidebar-foreground hover:bg-sidebar-accent/50",
                  )
                }
              >
                <Icon className="h-4 w-4" />
                {item.label}
              </NavLink>
            )
          })}
        </div>

        <div className="pt-4 border-t border-sidebar-border space-y-2">
          <button
            onClick={toggleTheme}
            className="flex w-full items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium text-sidebar-foreground hover:bg-sidebar-accent/50 transition-colors"
            title={`Chuyển sang ${theme === "dark" ? "light" : "dark"} mode`}
          >
            {theme === "dark" ? <Sun className="h-4 w-4" /> : <Moon className="h-4 w-4" />}
            {theme === "dark" ? "Light Mode" : "Dark Mode"}
          </button>
          <button className="flex w-full items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium text-sidebar-foreground hover:bg-sidebar-accent/50 transition-colors">
            <Settings className="h-4 w-4" />
            Settings
          </button>
          <button 
            onClick={handleLogout}
            className="flex w-full items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium text-sidebar-foreground hover:bg-sidebar-accent/50 transition-colors"
          >
            <LogOut className="h-4 w-4" />
            Logout
          </button>
        </div>
      </nav>
    </aside>
  )
}
