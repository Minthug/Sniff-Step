import { useState } from 'react'
import { useRouter } from 'next/router'

export const useAuthenticatedFetch = () => {
    const router = useRouter()
    const [isFetching, setIsFetching] = useState(false)

    const authenticatedFetch = async (url: string, options: RequestInit) => {
        const accessToken = localStorage.getItem('accessToken')
        const refreshToken = localStorage.getItem('refreshToken')
        const userId = localStorage.getItem('userId')

        if (!accessToken) {
            router.push('/login')
            return
        }

        options.headers = {
            ...options.headers,
            Authorization: `Bearer ${accessToken}`
        }

        try {
            setIsFetching(true)
            const response = await fetch(url, options)

            if (response.status === 401) {
                // 액세스 토큰이 만료된 경우 토큰을 갱신합니다.
                const res = await fetch(`/api/auth/refresh/${userId}`, {
                    method: 'GET',
                    headers: { 'Content-Type': 'application/json', Authorization: 'Bearer ' + refreshToken }
                })
                const { accessToken: newAccessToken } = await res.json()
                if (newAccessToken) {
                    localStorage.setItem('accessToken', newAccessToken)
                    ;(options.headers as { Authorization: string }).Authorization = `Bearer ${newAccessToken}`
                    setIsFetching(false)
                    return fetch(url, options)
                } else {
                    // 토큰 갱신에 실패한 경우 로그인 페이지로 리디렉션합니다.
                    router.push('/login')
                }
            }

            setIsFetching(false)
            return response
        } catch (error) {
            setIsFetching(false)
            throw error
        }
    }

    return { isFetching, authenticatedFetch }
}
