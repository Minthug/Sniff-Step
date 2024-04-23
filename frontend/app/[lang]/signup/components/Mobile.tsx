import React from 'react'
import { LocaleSignup } from '@/app/types/locales'
import { GoogleLogin, SeparateLine, SigninButton, SigninLogo, container } from '@/app/common'
import { useRouter } from 'next/navigation'

interface Props {
    lang: string
    text: LocaleSignup
}

export function Mobile({ lang, text }: Props) {
    const router = useRouter()
    return (
        <div className={container.autentication.mobile.section}>
            <div className={container.autentication.mobile.main}>
                <SigninLogo lang={lang} />
                <GoogleLogin className="active:bg-gray-800" theme="dark">
                    {text.signupGoogle}
                </GoogleLogin>
                <SeparateLine>or</SeparateLine>
                <SigninButton onClick={() => router.push(`/${lang}/signup/email-password`)} className="active:bg-slate-100 text-[14px]">
                    {text.signupEmail}
                </SigninButton>
                <div className="flex flex-wrap justify-center mb-4 text-center text-[12px] mt-8">
                    {text.introduceTermsOfService}&nbsp;
                    <button className="underline select-none">{text.termsOfService}</button>,&nbsp;
                    <button className="underline select-none">{text.privacyPolicy}</button>
                </div>
                <div className="flex flex-wrap justify-center text-center text-[12px]">
                    {text.introduceAlreadyHaveAccount}&nbsp;
                    <button className="underline select-none" onClick={() => router.push(`/${lang}/signin`)}>
                        {text.signin}
                    </button>
                </div>
            </div>
        </div>
    )
}
