import React from 'react'
import { MobileCategories, MobileMenu, MobileSearch, container } from '..'
import { useRouter } from 'next/navigation'
import { LocaleHeader, Locales } from '@/app/types/locales'

interface Props {
    lang: Locales
    text: LocaleHeader
    onMobileMenu: boolean
    onMobileSearch: boolean
    setOnMobileMenu: (onMobileMenu: boolean) => void
    setOnMobileSearch: (onMobileSearch: boolean) => void
}

export default function Mobile({ lang, text, onMobileMenu, onMobileSearch, setOnMobileMenu, setOnMobileSearch }: Props) {
    const router = useRouter()
    return (
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
    )
}
