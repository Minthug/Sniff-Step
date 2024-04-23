import React from 'react'
import Link from 'next/link'
import { D2CodingBold } from '@/app/fonts'
import { FaLongArrowAltLeft } from 'react-icons/fa'
import { GoogleLogin, SeparateLine, SigninButton, container } from '@/app/common'
import { useRouter } from 'next/navigation'
import { LocaleSignup } from '@/app/types/locales'

interface Props {
    lang: string
    text: LocaleSignup
}

export function Desktop({ lang, text }: Props) {
    const router = useRouter()
    return (
        <div className={container.autentication.desktop.section}>
            <div className={container.autentication.desktop.sidebar}>
                <Link
                    className="absolute left-8 h-[60px] flex gap-2 justify-center items-center animate-leftBounce z-10"
                    href={`/${lang}/signin`}
                >
                    <FaLongArrowAltLeft className="w-[20px] h-[20px]" />
                    <div className={`${D2CodingBold.className} text-[14px]`}>{text.signin}</div>
                </Link>
                <video
                    className="w-full h-full object-contain select-none bg-[#a6bee4]"
                    autoPlay
                    muted
                    loop
                    src="https://cdn.dribbble.com/users/1341046/screenshots/16057590/media/db8ececb62cdd03ddaa78fab991a4ec3.mp4"
                />
                <img className="absolute bottom-4 w-[140px] object-cover" src="/images/text-logo.png" alt="" />
            </div>
            <div className={container.autentication.desktop.main}>
                <div className={`${D2CodingBold.className} text-[28px] font-bold mb-8`}>{text.title}</div>
                <GoogleLogin theme="dark">{text.signupGoogle}</GoogleLogin>
                <SeparateLine className="mb-4">or</SeparateLine>
                <SigninButton onClick={() => router.push(`/${lang}/signup/email-password`)}>{text.signupEmail}</SigninButton>
                <div className="flex flex-wrap justify-center mb-4 text-[12px] mt-8 px-[60px]">
                    {text.introduceTermsOfService}
                    &nbsp;
                    <button className="underline select-none">{text.termsOfService}</button>
                    ,&nbsp;
                    <button className="underline select-none">{text.privacyPolicy}</button>
                </div>
                <div className="flex justify-center text-[14px]">
                    {text.introduceAlreadyHaveAccount}&nbsp;
                    <button className="underline select-none" onClick={() => router.push(`/${lang}/signin`)}>
                        {text.signin}
                    </button>
                </div>
            </div>
        </div>
    )
}
