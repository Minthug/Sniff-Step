'use client'

import React, { useCallback, useEffect, useState } from 'react'
import { container } from './styles'
import { AiOutlineSearch } from 'react-icons/ai'
import { D2CodingBold } from '../fonts'
import MobileCategories from './components/MobileCategories'
import MobileMenu from './components/MobileMenu'
import MobileSearch from './components/MobileSearch'
import { useRouter } from 'next/navigation'

export default function Header() {
    const [onMobileMenu, setOnMobileMenu] = useState(false)
    const [onMobileSearch, setOnMobileSearch] = useState(false)
    const [viewportWidth, setViewportWidth] = useState(0)
    const router = useRouter()
    const mobile = viewportWidth < 1280

    const handleResize = useCallback(() => {
        setViewportWidth(window.innerWidth)
    }, [])

    useEffect(() => {
        setViewportWidth(window.innerWidth)
        window.addEventListener('resize', handleResize)
        return () => {
            window.removeEventListener('resize', handleResize)
        }
    }, [])

    if (mobile) {
        return (
            <div
                className={`
                    ${container}
                    relative flex justify-between items-center py-2 bg-white
                `}
            >
                {onMobileSearch && <MobileSearch setOnMobileSearch={setOnMobileSearch} />}
                <div className={`${D2CodingBold.className} cursor-pointer`} onClick={() => router.push('/')}>
                    스니프앤스탭
                </div>
                <MobileMenu
                    onMobileMenu={onMobileMenu}
                    onMobileSearch={onMobileSearch}
                    setOnMobileSearch={setOnMobileSearch}
                    setOnMobileMenu={setOnMobileMenu}
                />
                <MobileCategories onMobileMenu={onMobileMenu} setOnMobileMenu={setOnMobileMenu} />
            </div>
        )
    }

    return (
        <div
            className={`
                ${container}
                flex justify-between items-center py-2 bg-white
            `}
        >
            <div className={`${D2CodingBold.className} cursor-pointer`} onClick={() => router.push('/')}>
                스니프앤스탭
            </div>
            <div className="flex gap-16 items-center">
                <button className="px-4 py-2">산책 시키기</button>
                <button className="px-4 py-2">산책 맡기기</button>
                <div className="relative">
                    <input
                        className="w-[400px] pl-4 pr-[40px] py-2 text-[14px] text-[#898989] bg-[#F2F3F6] rounded-[4px] outline-none"
                        onChange={() => {}}
                        type="text"
                        placeholder="동네 검색으로 산책인을 찾아보세요"
                    />
                    <div className="absolute top-[50%] right-0 translate-y-[-50%] w-[40px] h-[40px] flex justify-center items-center cursor-pointer">
                        <AiOutlineSearch className="text-[#898989]" />
                    </div>
                </div>
                <button className="px-4 py-2">로그인</button>
            </div>
        </div>
    )
}
