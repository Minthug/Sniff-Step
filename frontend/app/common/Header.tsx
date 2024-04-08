'use client'

import React, { useState } from 'react'
import { container } from './styles'
import { AiOutlineSearch } from 'react-icons/ai'
import { useRouter } from 'next/navigation'
import { MobileCategories, MobileMenu, MobileSearch } from './components'
import HeaderButton from './components/HeaderButton'
import { useResponsive } from '../hooks'

interface Props {
    lang: 'ko' | 'en'
}

export function Header({ lang }: Props) {
    const [onMobileMenu, setOnMobileMenu] = useState(false)
    const [onMobileSearch, setOnMobileSearch] = useState(false)
    const router = useRouter()
    const { mobile } = useResponsive()

    if (mobile) {
        return (
            <div
                className={`
                    ${container.header}
                    relative flex justify-between items-center py-2 bg-neutral-50
                `}
            >
                {onMobileSearch && <MobileSearch setOnMobileSearch={setOnMobileSearch} />}
                <img
                    className="w-[160px] h-[60px] cursor-pointer object-contain translate-x-[-20px]"
                    src="/text-logo.png"
                    onClick={() => router.push(`/${lang}`)}
                />
                <MobileMenu
                    onMobileMenu={onMobileMenu}
                    onMobileSearch={onMobileSearch}
                    setOnMobileSearch={setOnMobileSearch}
                    setOnMobileMenu={setOnMobileMenu}
                />
                <MobileCategories lang={lang} onMobileMenu={onMobileMenu} setOnMobileMenu={setOnMobileMenu} />
            </div>
        )
    }

    return (
        <div
            className={`
                ${container.header}
                flex justify-between items-center pb-8 pt-4
            `}
        >
            <img className="w-[80px] h-[80px] cursor-pointer" src="/logo1-removebg-preview.png" onClick={() => router.push(`/${lang}`)} />
            <div className="flex gap-8 items-center">
                <HeaderButton contents="산책 시키기" href={`/${lang}/register-walker`} />
                <HeaderButton contents="산책 맡기기" href={`/${lang}/assign-walker`} />
                <div className="relative">
                    <input
                        className="w-[400px] pl-8 pr-[60px] py-4 text-[14px] text-[#898989] border bg-white rounded-[100px] outline-none"
                        onChange={() => {}}
                        type="text"
                        placeholder="동네 검색으로 산책인을 찾아보세요!"
                    />
                    <div className="absolute top-[50%] right-4 translate-y-[-50%] w-[40px] h-[40px] flex justify-center items-center cursor-pointer">
                        <AiOutlineSearch className="text-[#898989]" />
                    </div>
                </div>
                <HeaderButton contents="로그인" href={`/${lang}/signin`} />
            </div>
        </div>
    )
}
