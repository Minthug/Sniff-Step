import { NextRequest, NextResponse } from 'next/server'

export async function GET(req: NextRequest) {
    const res = await fetch(process.env.JAVA_BACKEND_URL + '/v1/auth/google', {
        cache: 'no-store'
    })

    if (!res.ok) {
        const { message, error, statusCode } = await res.json()
        return NextResponse.json({ message, error }, { status: statusCode })
    }

    return NextResponse.json({ url: res.url }, { status: 200 })
}
