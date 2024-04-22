import { container } from '@/app/common'
import { D2CodingBold } from '@/app/fonts'
import { Locales } from '@/app/types/locales'
import Link from 'next/link'
import React, { useEffect, useState } from 'react'

interface Props {
    lang: Locales
}

export function MobileFirstSection({ lang }: Props) {
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
                        <div>반려견을 위한</div>
                        <div>우리 동네 산책 커뮤니티</div>
                        <Link
                            href={`/${lang}/boards`}
                            className={`
                                ${D2CodingBold.className}
                                hover:bg-green-600 hover:text-gray-100
                                active:bg-green-700
                                py-3 mt-4 bg-[#10b94e] rounded-lg font-[500] text-[18px] text-white text-center
                        `}
                        >
                            산책인 공고 구경하기
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    )
}
