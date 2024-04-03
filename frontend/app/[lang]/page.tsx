import React from 'react'
import { Locales } from '../types/locales'
import Link from 'next/link'
import { D2CodingBold } from '../fonts'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    return (
        <div className="p-20">
            <Link className={`${D2CodingBold.className}`} href="/[lang]/login" as={`/${lang}/login`}>
                login
            </Link>
        </div>
    )
}
