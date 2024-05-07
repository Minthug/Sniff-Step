import React from 'react'
import { AiOutlineMenu, AiOutlineSearch } from 'react-icons/ai'

interface Props {
    changeOnMobileMenu: () => void
    changeOnMobileSearch: () => void
}

export function MobileMenu({ changeOnMobileMenu, changeOnMobileSearch }: Props) {
    return (
        <div className="flex gap-4">
            <div className="w-[40px] h-[40px] flex justify-center items-center cursor-pointer select-none" onClick={changeOnMobileSearch}>
                <AiOutlineSearch className="text-[24px]" />
            </div>
            <div className="w-[40px] h-[40px] flex justify-center items-center cursor-pointer select-none" onClick={changeOnMobileMenu}>
                <AiOutlineMenu className="text-[24px]" />
            </div>
        </div>
    )
}
