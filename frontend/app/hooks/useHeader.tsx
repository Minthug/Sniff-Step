import React, { useState } from 'react'
import { Locales } from '../types/locales'
import { useRouter } from 'next/navigation'

export interface HeaderStates {
    search: string
    onMobileMenu: boolean
    onMobileSearch: boolean
    changeSearch: (e: React.ChangeEvent<HTMLInputElement>) => void
    changeOnMobileMenu: () => void
    changeOnMobileSearch: () => void
    handleSearch: (lang: Locales) => void
}

export default function useHeader() {
    const router = useRouter()
    const [search, setSearch] = useState('')
    const [onMobileMenu, setOnMobileMenu] = useState(false)
    const [onMobileSearch, setOnMobileSearch] = useState(false)

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

    return { search, onMobileMenu, onMobileSearch, changeSearch, changeOnMobileMenu, changeOnMobileSearch, handleSearch }
}
