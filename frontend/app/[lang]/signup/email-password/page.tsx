'use client'

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
    const router = useRouter()
    const [name, setName] = useState('')
    const [nickName, setNickName] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')
    const [isAgreed, setIsAgreed] = useState(false)

    return (
        <div className="relative w-full h-screen flex">
            <div className="w-[27.5%] min-w-[400px] h-full">
                <Link
                    className="absolute left-8 h-[60px] flex gap-2 justify-center items-center animate-leftBounce z-10"
                    href={`/${lang}/signup`}
                >
                    <FaLongArrowAltLeft className="w-[20px] h-[20px]" />
                    <div className={`${D2CodingBold.className} text-[14px]`}>Sign Up</div>
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
                <div className={`${D2CodingBold.className} text-[28px] mb-4`}>Sign up to Sniff & Step</div>
                <div className="flex gap-4 mb-4">
                    <div className="w-full">
                        <div className={`${D2CodingBold.className} mb-2`}>Name</div>
                        <SigninInput placeholder="Enter your name" type="text" onChange={setName} />
                    </div>
                    <div className="w-full">
                        <div className={`${D2CodingBold.className} mb-2`}>Nickname</div>
                        <SigninInput placeholder="Enter your nickname" type="text" onChange={setNickName} />
                    </div>
                </div>
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>Email</div>
                    <SigninInput placeholder="Enter your email" type="text" onChange={setEmail} />
                </div>
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>Password</div>
                    <SigninInput
                        placeholder="Please use at least 6 characters, using English and numbers"
                        type="password"
                        onChange={setPassword}
                    />
                </div>
                <div className="mb-4">
                    <div className={`${D2CodingBold.className} mb-2`}>Phone Number</div>
                    <SigninInput placeholder="01012341234" type="text" onChange={setPhoneNumber} />
                </div>
                <div className="flex items-center gap-4 mb-8">
                    <input
                        onChange={(e) => {
                            setIsAgreed(e.target.checked)
                        }}
                        className="w-[20px] h-[20px]"
                        type="checkbox"
                    />
                    <div className="flex flex-wrap text-[12px]">
                        <div>I agreed with Sniff & Steps&nbsp;</div>
                        <button className="underline select-none">Terms of Service</button>,&nbsp;
                        <button className="underline select-none">Privacy Policy</button>
                    </div>
                </div>
                <SigninButton theme="dark" onClick={() => {}}>
                    Sign up
                </SigninButton>
                <div className="flex justify-center items-center text-[12px]">
                    Already have an account?&nbsp;
                    <button className="underline select-none" onClick={() => router.push(`/${lang}/signin`)}>
                        Sign In
                    </button>
                </div>
            </div>
        </div>
    )
}
