import LayoutDefault from "@/components/layout/LayoutDefault"
import PrivateRoute from "@/components/PrivateRoute"
import { DashboardOverview } from "@/pages/Dashboard/Overview"
import { Login } from "@/pages/Login/Login"
export const routes = [
  {
    path: "/login",
    element: <Login />
  },
  {
    path: "/",
    element: <LayoutDefault />,
    children: [
     
      {
        element: <PrivateRoute />,
        children: [
        {
          path: "/",
          element: <DashboardOverview />
        },
        ]
      },
    ]
  }
]


// // Route paths constants
export const ROUTES = {
  HOME: "/",
  LOGIN: "/login",
  DASHBOARD: "/dashboard",
  MOVIES: "/dashboard/movies",
  SHOWTIMES: "/dashboard/showtimes", 
  THEATERS: "/dashboard/theaters",
  TICKETS: "/dashboard/tickets"
} as const