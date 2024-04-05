'use client'

import { GoogleLogin } from '@/app/common/GoogleLogin'
import SeparateLine from '@/app/common/SeparateLine'
import SigninButton from '@/app/common/SigninButton'
import { D2CodingBold } from '@/app/fonts'
import { Locales } from '@/app/types/locales'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import React from 'react'
import { FaLongArrowAltLeft } from 'react-icons/fa'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    const router = useRouter()
    return (
        <div className="relative w-full h-screen flex">
            <div className="w-[27.5%] min-w-[400px] h-full">
                <Link
                    className="absolute left-8 h-[60px] flex gap-2 justify-center items-center animate-leftBounce z-10"
                    href={`/${lang}/signin`}
                >
                    <FaLongArrowAltLeft className="w-[20px] h-[20px]" />
                    <div className={`${D2CodingBold.className} text-[14px]`}>Login</div>
                </Link>
                <video
                    className="w-full h-full object-contain select-none bg-[#a6bee4]"
                    autoPlay
                    muted
                    loop
                    src="https://cdn.dribbble.com/users/1341046/screenshots/16057590/media/db8ececb62cdd03ddaa78fab991a4ec3.mp4"
                />
                <img className="absolute bottom-4 w-[140px] object-cover" src="/text-logo.png" alt="" />
            </div>
            <div className="pl-[160px] w-[720px] h-full flex flex-col justify-center">
                <div className={`${D2CodingBold.className} text-[28px] font-bold mb-8`}>Sign up to Sniff & Step</div>
                <GoogleLogin theme="dark">Sign up with Google</GoogleLogin>
                <SeparateLine>or</SeparateLine>
                <SigninButton onClick={() => router.push(`/${lang}/signup/email-password`)}>Sign up with Email</SigninButton>
                <div className="flex flex-wrap justify-center mb-4 text-[12px] mt-8">
                    By creating an account you agree with our&nbsp;
                    <button className="underline select-none">Terms of Service</button>,&nbsp;
                    <button className="underline select-none">Privacy Policy</button>
                </div>
                <div className="flex justify-center text-[14px]">
                    Already have an account?&nbsp;
                    <button className="underline select-none" onClick={() => router.push(`/${lang}/signin`)}>
                        Sign In
                    </button>
                </div>
            </div>
        </div>
    )
}
