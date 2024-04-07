'use client'

import Link from 'next/link'
import React from 'react'

export default function NotFound() {
    return (
        <div className="w-full h-screen flex flex-col justify-center items-center">
            <div className="text-[48px] font-bold">Not found 404</div>
            <Link className="px-12 py-4 mt-4 border border-gray-200 rounded-xl select-none" href="/">
                Go back to Home
            </Link>
        </div>
    )
}
