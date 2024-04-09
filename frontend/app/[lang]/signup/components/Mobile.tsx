import React from 'react'
import { GoogleLogin, SeparateLine, SigninButton, SigninLogo, container } from '@/app/common'
import { useRouter } from 'next/navigation'

interface Props {
    lang: string
}

export function Mobile({ lang }: Props) {
    const router = useRouter()
    return (
        <div className={container.autentication.mobile.section}>
            <div className={container.autentication.mobile.main}>
                <SigninLogo lang={lang} />
                <GoogleLogin className="active:bg-gray-800" theme="dark">
                    Sign up with Google
                </GoogleLogin>
                <SeparateLine>or</SeparateLine>
                <SigninButton onClick={() => router.push(`/${lang}/signup/email-password`)} className="active:bg-slate-100 text-[14px]">
                    Sign up with Email
                </SigninButton>
                <div className="flex flex-wrap justify-center mb-4 text-center text-[12px] mt-8">
                    By creating an account you agree with our&nbsp;
                    <button className="underline select-none">Terms of Service</button>,&nbsp;
                    <button className="underline select-none">Privacy Policy</button>
                </div>
                <div className="flex flex-wrap justify-center text-center text-[12px]">
                    Already have an account?&nbsp;
                    <button className="underline select-none" onClick={() => router.push(`/${lang}/signin`)}>
                        Sign In
                    </button>
                </div>
            </div>
        </div>
    )
}
