import React from 'react'
import { MobileCategories, MobileMenu, MobileSearch, container } from '..'
import { useRouter } from 'next/navigation'
import { LocaleHeader, Locales } from '@/app/types/locales'
import { HeaderStates } from '@/app/hooks/useHeader'

interface Props {
    lang: Locales
    text: LocaleHeader
    headerStates: HeaderStates
}

export default function Mobile({ lang, text, headerStates }: Props) {
    const router = useRouter()
    const { search, onMobileMenu, onMobileSearch, changeSearch, changeOnMobileMenu, changeOnMobileSearch } = headerStates

    return (
        <div className={container.header.mobile}>
            {onMobileSearch && (
                <MobileSearch
                    lang={lang}
                    text={text}
                    search={search}
                    changeSearch={changeSearch}
                    changeOnMobileSearch={changeOnMobileSearch}
                />
            )}
            <img
                className="w-[160px] h-[60px] cursor-pointer object-contain translate-x-[-20px]"
                src="/images/text-logo.png"
                onClick={() => router.push(`/${lang}`)}
            />
            <MobileMenu changeOnMobileSearch={changeOnMobileSearch} changeOnMobileMenu={changeOnMobileMenu} />
            <MobileCategories lang={lang} text={text} onMobileMenu={onMobileMenu} changeOnMobileMenu={changeOnMobileMenu} />
        </div>
    )
}
