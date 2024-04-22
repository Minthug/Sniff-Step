import { D2CodingBold } from '@/app/fonts'
import { LocaleHeader } from '@/app/types/locales'
import { useRouter } from 'next/navigation'
import React, { HTMLAttributes } from 'react'

interface Props {
    lang: string
    text: LocaleHeader
    onMobileMenu: boolean
    setOnMobileMenu: (onMobileMenu: boolean) => void
}

export function MobileCategories({ lang, text, onMobileMenu, setOnMobileMenu }: Props) {
    const router = useRouter()

    return (
        <>
            <div
                onClick={() => setOnMobileMenu(!onMobileMenu)}
                style={{
                    height: onMobileMenu ? '100%' : 0,
                    minHeight: onMobileMenu ? 'calc(100vh - 56px)' : 0
                }}
                className={`absolute left-0 top-[76px] h-[100%] w-full bg-[#222] opacity-[60%] z-10`}
            ></div>
            <div
                style={{
                    height: onMobileMenu ? '151px' : '0px',
                    opacity: onMobileMenu ? 1 : 0,
                    transition: onMobileMenu ? 'height 0.3s, opacity 0.3s' : 'height 0.5s',
                    padding: onMobileMenu ? '0 0 16px 0' : '0'
                }}
                className={`
                    ${D2CodingBold.className}
                    absolute left-0 top-[76px] w-full items-center bg-neutral-50 text-[18px] z-10
                `}
            >
                {onMobileMenu && (
                    <>
                        <MobileCategory onClick={() => router.push(`/${lang}/register-walker`)}>{text.registerWalker}</MobileCategory>
                        <MobileCategory onClick={() => router.push(`/${lang}/boards`)}>{text.boards}</MobileCategory>
                        <MobileCategory onClick={() => router.push(`/${lang}/signin`)}>{text.login}</MobileCategory>
                    </>
                )}
            </div>
        </>
    )
}

function MobileCategory({ children, onClick }: HTMLAttributes<HTMLButtonElement>) {
    return (
        <button
            className={`
                hover:bg-slate-100 active:bg-slate-200
                w-full flex justify-center py-2 cursor-pointer
            `}
            onClick={onClick}
        >
            {children}
        </button>
    )
}
