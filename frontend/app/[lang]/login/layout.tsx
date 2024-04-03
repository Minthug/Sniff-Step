import { Locales } from '@/app/types/locales'
import React from 'react'

export default function layout({ children, params }: { children: React.ReactNode; params: { lang: Locales } }) {
    return <div className="m-20 border">{children}</div>
}
