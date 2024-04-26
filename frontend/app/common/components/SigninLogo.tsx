import { useRouter } from 'next/navigation'
import React from 'react'

interface Props {
    lang: string
}

export function SigninLogo({ lang }: Props) {
    const router = useRouter()
    return (
        <img
            className={`min-w-[150px] mb-4 active:bg-slate-100 cursor-pointer`}
            src="/images/text-logo.png"
            onClick={() => router.push(`/${lang}`)}
        />
    )
}
