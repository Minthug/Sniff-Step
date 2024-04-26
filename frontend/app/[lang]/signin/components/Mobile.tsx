import React from 'react'
import { D2CodingBold } from '@/app/fonts'
import { GoogleButton, SeparateLine, LargeButton, Input, TextLogo, container } from '@/app/common'
import { useRouter } from 'next/navigation'
import { LoginStates } from '@/app/hooks'
import { LocaleSignin } from '@/app/types/locales'

interface Props {
    lang: string
    text: LocaleSignin
    loginStates: LoginStates
}

export function Mobile({ lang, text, loginStates }: Props) {
    const router = useRouter()
    const { email, password, changeEmail, changePassword } = loginStates

    return (
        <div className={container.autentication.mobile.section}>
            <div className={container.autentication.mobile.main}>
                <TextLogo lang={lang} />
                <GoogleButton className="mb-4 active:bg-slate-100">{text.signinGoogle}</GoogleButton>
                <SeparateLine className="mb-0 text-[12px]">or</SeparateLine>
                <div className="my-4">
                    <div className={`${D2CodingBold.className} mb-1 text-[14px]`}>{text.email}</div>
                    <Input value={email} placeholder={text.emailPlaceholder} type="text" onChange={changeEmail} />
                </div>
                <div className="mb-8">
                    <div className="flex justify-between">
                        <div className={`${D2CodingBold.className} mb-1 text-[14px]`}>{text.password}</div>
                        <button
                            onClick={() => router.push(`/${lang}/find-password`)}
                            className={`
                                    ${D2CodingBold.className} text-[12px] underline select-none
                                    active:bg-slate-100
                                `}
                        >
                            {text.findPassword}
                        </button>
                    </div>
                    <Input value={password} placeholder={text.passwordPlaceholder} type="password" onChange={changePassword} />
                </div>
                <LargeButton className="active:bg-gray-800" theme="dark" onClick={() => {}}>
                    {text.signin}
                </LargeButton>
                <div className="flex gap-2 text-[12px] justify-center">
                    <div>{text.signupIntroduce}</div>
                    <button className="underline select-none active:bg-slate-100" onClick={() => router.push(`/${lang}/signup`)}>
                        {text.signup}
                    </button>
                </div>
            </div>
        </div>
    )
}
