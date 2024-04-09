import React from 'react'
import { D2CodingBold } from '@/app/fonts'
import { GoogleLogin, SeparateLine, SigninButton, SigninInput, SigninLogo, container } from '@/app/common'
import { useRouter } from 'next/navigation'
import { LoginStates } from '@/app/hooks'

interface Props {
    lang: string
    loginStates: LoginStates
}

export function Mobile({ lang, loginStates }: Props) {
    const router = useRouter()
    const { email, password, changeEmail, changePassword } = loginStates

    return (
        <div className={container.autentication.mobile.section}>
            <div className={container.autentication.mobile.main}>
                <SigninLogo lang={lang} />
                <GoogleLogin className="mb-4 active:bg-slate-100">Sign in with Google</GoogleLogin>
                <SeparateLine className="mb-0 text-[12px]">or</SeparateLine>
                <div className="my-4">
                    <div className={`${D2CodingBold.className} mb-1 text-[14px]`}>Username or Email</div>
                    <SigninInput value={email} placeholder="Enter your username or email" type="text" onChange={changeEmail} />
                </div>
                <div className="mb-8">
                    <div className="flex justify-between">
                        <div className={`${D2CodingBold.className} mb-1 text-[14px]`}>Password</div>
                        <button
                            onClick={() => router.push(`/${lang}/find-password`)}
                            className={`
                                    ${D2CodingBold.className} text-[12px] underline select-none
                                    active:bg-slate-100
                                `}
                        >
                            Find Password
                        </button>
                    </div>
                    <SigninInput value={password} placeholder="Enter your password" type="password" onChange={changePassword} />
                </div>
                <SigninButton className="active:bg-gray-800" theme="dark" onClick={() => {}}>
                    Sign in
                </SigninButton>
                <div className="flex gap-2 text-[12px] justify-center">
                    <div>Don't have account?</div>
                    <button className="underline select-none active:bg-slate-100" onClick={() => router.push(`/${lang}/signup`)}>
                        Sign Up
                    </button>
                </div>
            </div>
        </div>
    )
}
