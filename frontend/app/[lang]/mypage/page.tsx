'use client'

import React, { useEffect, useState } from 'react'
import { Locales } from '@/app/types/locales'
import { Profile } from '@/app/types/profile'
import { useFetch } from '@/app/hooks/useFetch'
import { Desktop, Mobile } from './components'

interface Props {
    params: { lang: Locales }
}

export default function page({ params: { lang } }: Props) {
    const { customFetch } = useFetch()
    const [profile, setProfile] = useState<Profile>()

    useEffect(() => {
        customFetch('/api/auth/profile', {
            method: 'GET'
        })
            .then(async (res) => {
                if (res) {
                    const { data } = await res.json()
                    setProfile(data)
                }
            })
            .catch((err) => console.log(err))
    }, [])

    return (
        <div>
            <Desktop profile={profile} />
            <Mobile profile={profile} />
        </div>
    )
}
