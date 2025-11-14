import { RoomCard, AddRoomButton } from "@/components/rooms";
import type { Room } from "@/types/room";

// Mock data - replace with actual API call
const mockRooms: Room[] = [
  {
    id: "1",
    name: "Phòng 1",
    type: "Standard",
    capacity: 120,
    status: "active",
    currentMovie: "Avatar: The Way of Water",
    nextShowtime: "14:00",
  },
  {
    id: "2",
    name: "Phòng 2",
    type: "Premium",
    capacity: 150,
    status: "active",
    currentMovie: "Top Gun: Maverick",
    nextShowtime: "15:30",
  },
  {
    id: "3",
    name: "Phòng 3",
    type: "Standard",
    capacity: 100,
    status: "active",
    currentMovie: "The Batman",
    nextShowtime: "16:30",
  },
  {
    id: "4",
    name: "Phòng 4",
    type: "IMAX",
    capacity: 200,
    status: "maintenance",
  },
  {
    id: "5",
    name: "Phòng 5",
    type: "VIP",
    capacity: 80,
    status: "active",
    currentMovie: "Spider-Man: No Way Home",
    nextShowtime: "18:00",
  },
  {
    id: "6",
    name: "Phòng 6",
    type: "Standard",
    capacity: 120,
    status: "active",
    currentMovie: "Avatar: The Way of Water",
    nextShowtime: "19:30",
  },
];

export function RoomList() {
  const handleEdit = (room: Room) => {
    console.log("Edit room:", room);
    // TODO: Implement edit functionality
  };

  const handleViewSchedule = (room: Room) => {
    console.log("View schedule:", room);
    // TODO: Implement view schedule functionality
  };

  const handleAddRoom = () => {
    console.log("Add new room");
    // TODO: Implement add room functionality
  };

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between border-b border-border pb-4">
        <div>
          <h1 className="text-3xl font-bold text-foreground">Phòng chiếu</h1>
          <p className="text-muted-foreground mt-1">
            Quản lý thông tin và trạng thái các phòng chiếu
          </p>
        </div>
        <AddRoomButton onClick={handleAddRoom} />
      </div>

      {/* Rooms Grid */}
      <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
        {mockRooms.map((room) => (
          <RoomCard
            key={room.id}
            room={room}
            onEdit={handleEdit}
            onViewSchedule={handleViewSchedule}
          />
        ))}
      </div>
    </div>
  );
}
