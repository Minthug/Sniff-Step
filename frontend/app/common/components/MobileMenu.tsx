import React from 'react'
import { AiOutlineMenu, AiOutlineSearch } from 'react-icons/ai'

interface Props {
    onMobileMenu: boolean
    onMobileSearch: boolean
    setOnMobileMenu: (onMobileMenu: boolean) => void
    setOnMobileSearch: (onMobileSearch: boolean) => void
}

export default function MobileMenu({ onMobileMenu, onMobileSearch, setOnMobileMenu, setOnMobileSearch }: Props) {
    return (
        <div className="flex gap-4">
            <div
                className="w-[40px] h-[40px] flex justify-center items-center cursor-pointer select-none"
                onClick={() => setOnMobileSearch(!onMobileSearch)}
            >
                <AiOutlineSearch className="text-[24px]" />
            </div>
            <div
                className="w-[40px] h-[40px] flex justify-center items-center cursor-pointer select-none"
                onClick={() => setOnMobileMenu(!onMobileMenu)}
            >
                <AiOutlineMenu className="text-[24px]" />
            </div>
        </div>
    )
}
