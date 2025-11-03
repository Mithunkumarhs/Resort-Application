import axios from "axios";

const BASE_URL = "http://localhost:8080"; // Ensure this matches your backend URL

const ApiService = {
  getHeaders() {
    const token = localStorage.getItem("token");
    return token ? { Authorization: `Bearer ${token}` } : {};
  },

  async login(email, password) {
    try {
      const response = await axios.post(`${BASE_URL}/auth/login`, { email, password });
      localStorage.setItem("token", response.data.token);
      localStorage.setItem("role", response.data.role); // Store the role
      return response.data;
    } catch (error) {
      return error.response?.data || { message: "Login failed" };
    }
  },

  async register(userData) {
    return axios.post(`${BASE_URL}/auth/register`, userData);
  },

  async getAllRooms() {
    return axios.get(`${BASE_URL}/rooms/all`);
  },

  async getAvailableRooms(checkInDate, checkOutDate, roomType) {
    return axios.get(`${BASE_URL}/rooms/available-rooms-by-date-and-type`, {
      params: { checkInDate, checkOutDate, roomType },
    });
  },

  async bookRoom(roomId, userId, bookingData) {
    return axios.post(`${BASE_URL}/booking/book-room/${roomId}/${userId}`, bookingData, {
      headers: this.getHeaders(),
    });
  },

  async getUserProfile() {
    return axios.get(`${BASE_URL}/users/get-logged-in-profile-info`, {
      headers: this.getHeaders(),
    });
  },

  isAuthenticated() {
    return !!localStorage.getItem("token");
  },

  async getRoomTypes() {
  const response = await axios.get(`${BASE_URL}/rooms/types`);
  return response.data; // <-- It's just an array, not wrapped in { roomList: ... }
},

  isAdmin() {
    const role = localStorage.getItem("role");
    return role === "ADMIN"; // Ensure this matches your backend role values
  },

  isUser() {
    const role = localStorage.getItem("role");
    return role === "USER"; // Ensure this matches your backend role values
  },
};

export default ApiService;
