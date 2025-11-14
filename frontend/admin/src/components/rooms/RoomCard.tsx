import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Monitor, Users } from "lucide-react";
import type { Room } from "@/types/room";
import { RoomStatusBadge } from "./RoomStatusBadge";

interface RoomCardProps {
  room: Room;
  onEdit?: (room: Room) => void;
  onViewSchedule?: (room: Room) => void;
}

export function RoomCard({ room, onEdit, onViewSchedule }: RoomCardProps) {
  return (
    <Card className="overflow-hidden transition-shadow hover:shadow-md">
      <CardContent className="p-0">
        <div className="space-y-4 p-5">
          {/* Header with icon and status */}
          <div className="flex items-start justify-between">
            <div className="flex items-center gap-3">
              <div className="flex h-12 w-12 items-center justify-center rounded-lg bg-blue-50">
                <Monitor className="h-6 w-6 text-blue-600" />
              </div>
              <div>
                <h3 className="text-lg font-semibold text-gray-900">
                  {room.name}
                </h3>
                <p className="text-sm text-gray-500">{room.type}</p>
              </div>
            </div>
            <RoomStatusBadge status={room.status} />
          </div>

          {/* Capacity */}
          <div className="flex items-center gap-2 text-gray-700">
            <Users className="h-4 w-4 text-gray-400" />
            <span className="text-sm">
              Sức chứa: <span className="font-medium">{room.capacity} ghế</span>
            </span>
          </div>

          {/* Current Movie and Next Showtime */}
          {room.status === "active" && room.currentMovie ? (
            <div className="space-y-2 rounded-lg bg-gray-50 p-3">
              <div>
                <p className="text-xs text-gray-500">Đang chiếu</p>
                <p className="font-medium text-gray-900">{room.currentMovie}</p>
              </div>
              {room.nextShowtime && (
                <div>
                  <p className="text-xs text-gray-500">Suất chiếu tiếp theo</p>
                  <p className="font-medium text-blue-600">
                    {room.nextShowtime}
                  </p>
                </div>
              )}
            </div>
          ) : (
            <div className="rounded-lg bg-gray-50 p-3">
              <p className="text-sm italic text-gray-500">
                {room.status === "maintenance"
                  ? "Không có lịch chiếu"
                  : "Chưa có lịch chiếu"}
              </p>
            </div>
          )}

          {/* Action Buttons */}
          <div className="grid grid-cols-2 gap-2.5 pt-2">
            <Button
              variant="outline"
              className="w-full"
              onClick={() => onEdit?.(room)}
            >
              Chỉnh sửa
            </Button>
            <Button
              variant="default"
              className="w-full"
              onClick={() => onViewSchedule?.(room)}
            >
              Lịch chiếu
            </Button>
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
