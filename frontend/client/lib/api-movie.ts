import axios from 'axios'

// API base URL - Bao gồm context-path từ backend
const API_URL = 'http://localhost:8080/api/theater-mgnt'

// Tạo axios instance
const api = axios.create({
  baseURL: API_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// ==================== MOVIE APIs ====================

/**
 * Lấy danh sách phim đang chiếu (NOW_SHOWING)
 */
export const getNowShowingMovies = async () => {
  try {
    console.log('🎬 Fetching NOW_SHOWING movies...')

    // Backend có endpoint riêng cho now-showing
    const response = await api.get('/movies/now-showing')

    console.log('✅ Response:', response.data)

    // Backend trả về: { code, message, result: [...] }
    return response.data.data || []

  } catch (error) {
    console.error('❌ Error fetching NOW_SHOWING movies:', error)

    if (axios.isAxiosError(error)) {
      console.error('Response data:', error.response?.data)
      console.error('Response status:', error.response?.status)
    }

    return []
  }
}

/**
 * Lấy danh sách phim sắp chiếu (COMING_SOON)
 */
export const getComingSoonMovies = async () => {
  try {
    console.log('🎬 Fetching COMING_SOON movies...')

    // Backend có endpoint riêng cho coming-soon
    const response = await api.get('/movies/coming-soon')

    console.log('✅ Response:', response.data)

    return response.data.data || []

  } catch (error) {
    console.error('❌ Error fetching COMING_SOON movies:', error)

    if (axios.isAxiosError(error)) {
      console.error('Response data:', error.response?.data)
      console.error('Response status:', error.response?.status)
    }

    return []
  }
}

/**
 * Lấy chi tiết 1 phim theo ID
 */
export const getMovieById = async (id: number | string) => {
  try {
    console.log(`🎬 Fetching movie details for ID: ${id}`)

    const response = await api.get(`/movies/${id}`)

    console.log('✅ Movie details:', response.data)

    return response.data.result

  } catch (error) {
    console.error(`❌ Error fetching movie ${id}:`, error)

    if (axios.isAxiosError(error)) {
      if (error.response?.status === 404) {
        console.error('Movie not found')
      }
    }

    return null
  }
}

/**
 * Lấy danh sách tất cả genres (nếu có endpoint)
 */
export const getAllGenres = async () => {
  try {
    console.log('🎭 Fetching genres...')

    const response = await api.get('/genres')

    console.log('✅ Genres:', response.data)

    return response.data.result || []

  } catch (error) {
    console.error('❌ Error fetching genres:', error)
    return []
  }
}

// ==================== HELPER FUNCTIONS ====================

/**
 * Convert backend movie format to frontend display format
 * Backend MovieSimpleResponse: { id, title, posterUrl, durationMinutes, releaseDate, ageRatingName, genreNames, status }
 * Backend MovieResponse: có thêm description, director, cast, trailerUrl...
 */
export const mapMovieForDisplay = (movie: any) => {
  if (!movie) return null

  return {
    id: movie.id || '',
    title: movie.title || '',
    genre: movie.genreNames || [], // Backend trả về genreNames (array)
    rating: movie.ageRatingName || 'NR', // Backend trả về ageRatingName (string)
    duration: movie.durationMinutes || 0, // Backend dùng durationMinutes
    releaseDate: movie.releaseDate || '',
    poster: movie.posterUrl || '/placeholder.svg',
    description: movie.description || '',
    trailerUrl: movie.trailerUrl || '',
    director: movie.director || 'Unknown',
    cast: movie.cast ? movie.cast.split(',').map((c: string) => c.trim()) : [],
  }
}

/**
 * Check if backend is reachable
 */
export const checkBackendHealth = async () => {
  try {
    const response = await axios.get(`${API_URL}/movies`, { timeout: 3000 })
    console.log('✅ Backend is reachable')
    return true
  } catch (error) {
    console.error('❌ Backend is NOT reachable')
    console.error('Please make sure:')
    console.error('1. Backend is running: mvnw spring-boot:run')
    console.error('2. Backend is on port 8080')
    console.error('3. CORS is configured')
    return false
  }
}

export default api