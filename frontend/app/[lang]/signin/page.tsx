'use client'

import { GoogleLogin } from '@/app/common/GoogleLogin'
import SeparateLine from '@/app/common/SeparateLine'
import SigninButton from '@/app/common/SigninButton'
import SigninInput from '@/app/common/SigninInput'
import { D2CodingBold } from '@/app/fonts'
import { Locales } from '@/app/types/locales'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import React, { useState } from 'react'
import { FaLongArrowAltLeft } from 'react-icons/fa'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const router = useRouter()

    return (
        <div className="relative w-full h-screen flex">
            <div className="w-[27.5%] min-w-[400px] h-full">
                <Link className="absolute left-8 h-[60px] flex gap-2 justify-center items-center animate-leftBounce" href={`/${lang}/`}>
                    <FaLongArrowAltLeft className="w-[20px] h-[20px]" />
                    <div className={`${D2CodingBold.className}`}>Home</div>
                </Link>
                <img
                    className="w-full h-full object-contain select-none bg-[#C9E2EB]"
                    src="https://cdn.dribbble.com/users/338126/screenshots/15483287/media/2f03c8290d612078b76883e579d4fd99.gif"
                />
                <img className="absolute bottom-4 w-[140px] object-cover" src="/text-logo.png" alt="" />
            </div>
            <div className="pl-[160px] w-[720px] h-full flex flex-col justify-center">
                <div className={`${D2CodingBold.className} text-[28px] font-bold mb-8`}>Sign in to Sniff & Step</div>
                <GoogleLogin>Sign in with Google</GoogleLogin>
                <SeparateLine>or sign in with email</SeparateLine>
                <div className="my-4">
                    <div className={`${D2CodingBold.className} mb-1`}>Username or Email</div>
                    <SigninInput placeholder="Enter your username or email" type="text" onChange={setEmail} />
                </div>
                <div className="mb-8">
                    <div className="flex justify-between">
                        <div className={`${D2CodingBold.className} mb-1`}>Password</div>
                        <button
                            onClick={() => router.push(`/${lang}/find-password`)}
                            className={`${D2CodingBold.className} text-[12px] underline select-none`}
                        >
                            Find Password
                        </button>
                    </div>
                    <SigninInput placeholder="Enter your password" type="password" onChange={setPassword} />
                </div>
                <SigninButton theme="dark" onClick={() => {}}>
                    Sign in
                </SigninButton>
                <div className="flex gap-2 text-[12px] justify-center">
                    <div>Don't have account?</div>
                    <button className="underline select-none" onClick={() => router.push(`/${lang}/signup`)}>
                        Sign Up
                    </button>
                </div>
            </div>
        </div>
    )
}
