'use client'

import React, { useState } from 'react'
import { container } from '../styles'
import { AiOutlineSearch } from 'react-icons/ai'
import { useRouter } from 'next/navigation'
import Button from '../components/Button'
import { LocaleHeader } from '../../types/locales'
import { MobileCategories, MobileMenu, MobileSearch } from '.'

interface Props {
    lang: 'ko' | 'en'
    text: LocaleHeader
}

export function Header({ lang, text }: Props) {
    const [onMobileMenu, setOnMobileMenu] = useState(false)
    const [onMobileSearch, setOnMobileSearch] = useState(false)
    const router = useRouter()

    return (
        <>
            <div
                className={`
                ${container.header} 
                xl:flex
                justify-between items-center pb-4 pt-4 hidden
            `}
            >
                <img
                    className="w-[220px] h-[60px] cursor-pointer object-contain translate-x-[-20px]"
                    src="/images/text-logo-1.png"
                    onClick={() => router.push(`/${lang}`)}
                />
                <div className="flex gap-10 items-center">
                    <Button contents={text.registerWalker} href={`/${lang}/register-walker`} />
                    <Button contents={text.boards} href={`/${lang}/boards`} />
                    <div className="relative">
                        <input
                            className="w-[400px] pl-8 pr-[60px] py-4 text-[16px] text-black border bg-white rounded-[100px] outline-none"
                            onChange={() => {}}
                            type="text"
                            placeholder={text.findMyLocal}
                        />
                        <div className="absolute top-[50%] right-4 translate-y-[-50%] w-[40px] h-[40px] flex justify-center items-center cursor-pointer">
                            <AiOutlineSearch className="text-[#898989]" />
                        </div>
                    </div>
                    <Button contents={text.login} href={`/${lang}/signin`} />
                </div>
            </div>

            <div
                className={`
                    ${container.header}
                    xl:hidden
                    fixed top-0 w-full flex justify-between items-center py-2 bg-[#fcfcfc] z-20
                `}
            >
                {onMobileSearch && <MobileSearch text={text} setOnMobileSearch={setOnMobileSearch} />}
                <img
                    className="w-[160px] h-[60px] cursor-pointer object-contain translate-x-[-20px]"
                    src="/images/text-logo.png"
                    onClick={() => router.push(`/${lang}`)}
                />
                <MobileMenu
                    onMobileMenu={onMobileMenu}
                    onMobileSearch={onMobileSearch}
                    setOnMobileSearch={setOnMobileSearch}
                    setOnMobileMenu={setOnMobileMenu}
                />
                <MobileCategories lang={lang} text={text} onMobileMenu={onMobileMenu} setOnMobileMenu={setOnMobileMenu} />
            </div>
        </>
    )
}
