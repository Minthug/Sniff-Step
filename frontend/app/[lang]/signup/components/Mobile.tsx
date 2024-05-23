import React from 'react'
import { LocaleSignup } from '@/app/types/locales'
import { GoogleButton, SeparateLine, LargeButton, TextLogo, container } from '@/app/common'
import { LoginStates } from '@/app/hooks'
import { useRouter } from 'next/navigation'
import Link from 'next/link'

interface Props {
    lang: string
    text: LocaleSignup
    loginStates: LoginStates
}

export function Mobile({ lang, text, loginStates }: Props) {
    const router = useRouter()
    const { handleGoogleLogin } = loginStates

    return (
        <div className={container.autentication.mobile.section}>
            <div className={container.autentication.mobile.main}>
                <TextLogo lang={lang} />
                <GoogleButton className="active:bg-gray-800" theme="dark" onClick={handleGoogleLogin}>
                    {text.signupGoogle}
                </GoogleButton>
                <SeparateLine>or</SeparateLine>
                <LargeButton onClick={() => router.push(`/${lang}/signup/email-password`)} className="active:bg-slate-100 text-[14px]">
                    {text.signupEmail}
                </LargeButton>
                <div className="flex flex-wrap justify-center mb-4 text-center text-[12px] mt-8">
                    {text.introduceTermsOfService}&nbsp;
                    <button className="underline select-none">{text.termsOfService}</button>,&nbsp;
                    <button className="underline select-none">{text.privacyPolicy}</button>
                </div>
                <div className="flex flex-wrap justify-center text-center text-[12px]">
                    {text.introduceAlreadyHaveAccount}&nbsp;
                    <Link className="underline select-none" href={`/${lang}/signin`}>
                        {text.signin}
                    </Link>
                </div>
            </div>
        </div>
    )
}
