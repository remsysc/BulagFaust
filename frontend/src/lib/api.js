// ─── Token / user helpers (localStorage) ────────────────────────────────────
const TOKEN_KEY = 'bf_token'
const USER_KEY  = 'bf_user'

export const getToken  = () => localStorage.getItem(TOKEN_KEY)
export const setToken  = (t) => localStorage.setItem(TOKEN_KEY, t)
export const removeToken = () => localStorage.removeItem(TOKEN_KEY)

export const getUser   = () => {
  try { return JSON.parse(localStorage.getItem(USER_KEY)) } catch { return null }
}
export const setUser   = (u) => localStorage.setItem(USER_KEY, JSON.stringify(u))
export const removeUser = () => localStorage.removeItem(USER_KEY)

// ─── Base fetch wrapper ──────────────────────────────────────────────────────
const BASE = '/api/v1'

async function request(method, path, body) {
  const headers = { 'Content-Type': 'application/json' }
  const token = getToken()
  if (token) headers['Authorization'] = `Bearer ${token}`

  const res = await fetch(`${BASE}${path}`, {
    method,
    headers,
    body: body != null ? JSON.stringify(body) : undefined,
  })

  // Parse JSON regardless of status so we can read error messages
  const json = await res.json().catch(() => null)

  if (!res.ok) {
    // Spring Boot GlobalExceptionHandler returns { code, message, timestamp }
    throw new Error(json?.message ?? `HTTP ${res.status}`)
  }

  // Unwrap ApiResponse<T> envelope → return data field
  return json?.data ?? json
}

const get  = (path)        => request('GET',    path)
const post = (path, body)  => request('POST',   path, body)
const put  = (path, body)  => request('PUT',    path, body)
const del  = (path)        => request('DELETE', path)

// ─── Auth ────────────────────────────────────────────────────────────────────
export const login    = (email, password) => post('/auth/login',    { email, password })
export const register = (username, email, password) => post('/auth/register', { username, email, password })

// ─── Posts ───────────────────────────────────────────────────────────────────
export const getPosts = (categoryId, tagId) => {
  const params = new URLSearchParams()
  if (categoryId) params.set('categoryId', categoryId)
  if (tagId)      params.set('tagId',      tagId)
  const qs = params.toString()
  return get(`/posts${qs ? `?${qs}` : ''}`)
}

// ─── Categories ──────────────────────────────────────────────────────────────
export const getCategories    = ()               => get(`/categories`)
export const createCategory   = (name)           => post(`/categories`,     { name })
export const updateCategory   = (id, name)       => put(`/categories/${id}`, { name })
export const deleteCategory   = (id)             => del(`/categories/${id}`)

// ─── Tags ────────────────────────────────────────────────────────────────────
export const getTags    = ()     => get(`/tags`)
export const createTag  = (name) => post(`/tags`, { name })
export const deleteTag  = (id)   => del(`/tags/${id}`)
