import React from 'react'
import { LocaleFooter } from '@/app/types/locales'
import { Button, container } from '..'

interface Props {
    lang: string
    text: LocaleFooter
}

export function Mobile({ lang, text }: Props) {
    return (
        <footer className={container.footer.mobile}>
            <div className="flex justify-between">
                <div className="w-full flex flex-col">
                    <img className="w-[96px]" src="/images/text-logo-1.png" alt="" />
                    <div className="flex flex-wrap gap-8 gap-y-0">
                        <Button className="text-sm" contents={text.home} href={`/${lang}`} />
                        <Button className="text-sm" contents={text.registerWalker} href={`/${lang}/register-walker`} />
                        <Button className="text-sm" contents={text.boards} href={`/${lang}/boards`} />
                        <Button className="text-sm" contents={text.login} href={`/${lang}/signin`} />
                        <Button className="text-sm" contents={text.language} href={`/${lang === 'en' ? 'ko' : 'en'}`} />
                    </div>
                </div>
            </div>
            <div className="flex flex-wrap gap-4 justify-between text-[12px] text-gray-400">
                <div className="flex items-center gap-4">
                    <div>@2024 Sniff & Step</div>
                    <div>Terms</div>
                    <div>Privacy</div>
                </div>
                <div className="flex items-center gap-4">
                    <div>@Minthug</div>
                    <div>@VVSOGI</div>
                </div>
            </div>
        </footer>
    )
}
