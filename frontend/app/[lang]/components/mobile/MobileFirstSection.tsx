'use client'

import React, { useEffect, useState } from 'react'
import Link from 'next/link'
import { LocaleHome, Locales } from '@/app/types/locales'
import { container } from '@/app/common'

interface Props {
    lang: Locales
    text: LocaleHome
}

export function MobileFirstSection({ lang, text }: Props) {
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        setLoading(false)
    }, [])

    return (
        <div className={`${container.home.mobile} relative min-h-[calc(100vh-76px)] h-full flex items-center select-none mt-[76px]`}>
            <div className="relative flex-1">
                <div className="flex flex-col gap-[40px]">
                    <div className="flex-1 flex justify-center items-center">
                        <img className="max-w-[500px] w-full object-contain" src="/images/main/1-section.png" alt="1-section" />
                    </div>
                    <div
                        style={{
                            opacity: loading ? 0 : 1,
                            transform: loading ? 'translateY(20px)' : 'translateY(0)'
                        }}
                        className={`h-full flex flex-col gap-2 text-[24px] font-[800] z-10 transition-all duration-500 whitespace-pre`}
                    >
                        <div>{text.section1.catchPhrase1}</div>
                        <div>{text.section1.catchPhrase2}</div>
                        <Link
                            href={`/${lang}/boards`}
                            className={`
                                hover:bg-green-600 hover:text-gray-100
                                active:bg-green-700
                                py-3 mt-4 bg-[#10b94e] rounded-lg font-[500] text-[18px] text-white text-center
                        `}
                        >
                            {text.Link}
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    )
}
