import React, { useEffect, useState } from 'react'
import { Locales } from '@/app/types/locales'
import { useRouter } from 'next/navigation'

export interface HeaderStates {
    loading: boolean
    search: string
    onMobileMenu: boolean
    onMobileSearch: boolean
    accessToken: string
    changeSearch: (e: React.ChangeEvent<HTMLInputElement>) => void
    changeOnMobileMenu: () => void
    changeOnMobileSearch: () => void
    handleSearch: (lang: Locales) => void
}

export default function useHeader() {
    const router = useRouter()
    const [loading, setLoading] = useState(true)
    const [search, setSearch] = useState('')
    const [onMobileMenu, setOnMobileMenu] = useState(false)
    const [onMobileSearch, setOnMobileSearch] = useState(false)
    const [accessToken, setAccessToken] = useState('')

    useEffect(() => {
        setLoading(false)
        const getAccessToken = localStorage.getItem('accessToken')
        if (getAccessToken) {
            setAccessToken(getAccessToken)
            return
        }

        fetch('/api/auth/google-profile', {
            credentials: 'include'
        }).then(async (res) => {
            const { data } = await res.json()
            if (data) {
                const googleAccessToken = data.value
                setAccessToken(googleAccessToken)
                localStorage.setItem('accessToken', googleAccessToken)
                const getGoogleProfile = await fetch('/api/auth/profile', {
                    headers: {
                        Authorization: `Bearer ${googleAccessToken}`
                    }
                })
                const profile = await getGoogleProfile.json()
                localStorage.setItem('userId', profile.data.id)
            }
        })
    }, [])

    const changeSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearch(e.target.value)
    }

    const changeOnMobileMenu = () => {
        setOnMobileMenu(!onMobileMenu)
    }

    const changeOnMobileSearch = () => {
        setOnMobileSearch(!onMobileSearch)
    }

    const handleSearch = (lang: Locales) => {
        if (search === '') {
            return router.push(`/${lang}/boards`)
        }
        return router.push(`/${lang}/boards/search?keyword=${search}&reload=true`)
    }

    return {
        loading,
        search,
        onMobileMenu,
        onMobileSearch,
        accessToken,
        changeSearch,
        changeOnMobileMenu,
        changeOnMobileSearch,
        handleSearch
    }
}
