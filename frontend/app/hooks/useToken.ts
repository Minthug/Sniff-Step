import { useEffect, useState } from 'react'

const useToken = () => {
    const [tokens, setTokens] = useState<{ accessToken: string | null; refreshToken: string | null }>({
        accessToken: null,
        refreshToken: null
    })

    useEffect(() => {
        const storedAccessToken = localStorage.getItem('accessToken')
        const storedRefreshToken = localStorage.getItem('refreshToken')

        setTokens({
            accessToken: storedAccessToken,
            refreshToken: storedRefreshToken
        })
    }, [])

    return tokens
}

export default useToken
