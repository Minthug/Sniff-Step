import { useRouter } from 'next/navigation'
import React, { useState } from 'react'

export interface HeaderStates {
    search: string
    onMobileMenu: boolean
    onMobileSearch: boolean
    changeSearch: (e: React.ChangeEvent<HTMLInputElement>) => void
    changeOnMobileMenu: () => void
    changeOnMobileSearch: () => void
}

export default function useHeader() {
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

    return { search, onMobileMenu, onMobileSearch, changeSearch, changeOnMobileMenu, changeOnMobileSearch }
}
