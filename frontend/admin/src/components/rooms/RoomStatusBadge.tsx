import { cn } from "@/lib/utils";
import type { RoomStatus } from "@/types/room";

interface RoomStatusBadgeProps {
  status: RoomStatus;
  className?: string;
}

export function RoomStatusBadge({ status, className }: RoomStatusBadgeProps) {
  const isActive = status === "active";

  return (
    <span
      className={cn(
        "inline-flex items-center rounded-md px-2.5 py-1 text-xs font-medium",
        isActive
          ? "bg-emerald-50 text-emerald-700"
          : "bg-gray-100 text-gray-700",
        className
      )}
    >
      {isActive ? "Đang hoạt động" : "Bảo trì"}
    </span>
  );
}
