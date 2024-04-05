import { D2CodingLight } from '@/app/fonts'
import { useRouter } from 'next/navigation'
import React from 'react'

interface Props {
    contents: string
    href: string
}

export default function HeaderButton({ contents, href }: Props) {
    const router = useRouter()
    return (
        <button
            className={`
                        ${D2CodingLight.className}
                        [&>div]:hover:w-full
                        relative py-2 text-[14px] tracking-[-0.12rem]
            `}
            onClick={() => router.push(href)}
        >
            {contents}
            <div
                className={`
                            w-0 h-[2px] bg-red-500 rounded-xl transition-all duration-300
                        `}
            />
        </button>
    )
}
