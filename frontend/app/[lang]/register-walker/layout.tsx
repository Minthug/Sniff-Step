import { Header, container } from '@/app/common'
import { Locales } from '@/app/types/locales'
import React from 'react'

interface Props {
    children: React.ReactNode
    params: { lang: Locales }
}

export default function page({ children, params: { lang } }: Props) {
    return (
        <div>
            <Header lang={lang} />
            <section className={container.section}>{children}</section>
        </div>
    )
}
