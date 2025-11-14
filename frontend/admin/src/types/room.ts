export type RoomStatus = "active" | "maintenance";

export type RoomType = "Standard" | "Premium" | "VIP" | "IMAX";

export interface Room {
  id: string;
  name: string;
  type: RoomType;
  capacity: number;
  status: RoomStatus;
  currentMovie?: string;
  nextShowtime?: string;
}
